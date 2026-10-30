package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.entity.Tarefa;
import br.com.api.clearSpaces.repository.TarefaRepository;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.UserCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class BackupFotosTarefaService {

    private final TarefaRepository tarefaRepository;

    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String clientSecret;

    @Value("${GOOGLE_REFRESH_TOKEN}")
    private String refreshToken;

    @Value("${GOOGLE_DRIVE_FOLDER_ID}")
    private String folderId;

    public BackupFotosTarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "America/Sao_Paulo")
    @Transactional
    public void realizarBackupELimpezaFotos() {
        List<Tarefa> tarefasComFoto = tarefaRepository.buscarTarefasComFotos();

        if (tarefasComFoto.isEmpty()) {
            return;
        }

        File pastaBackup = new File(System.getProperty("java.io.tmpdir"), "backups_tarefas");
        if (!pastaBackup.exists() && !pastaBackup.mkdirs()) {
            System.err.println("Não foi possível criar a pasta de backup");
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File arquivoZip = new File(pastaBackup, "fotos_tarefas_" + timestamp + ".zip");

        // 1) Gera o ZIP
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(arquivoZip))) {
            for (Tarefa tarefa : tarefasComFoto) {
                if (tarefa.getFoto_antes() != null && tarefa.getFoto_antes().length > 0) {
                    zos.putNextEntry(new ZipEntry("tarefa_" + tarefa.getId() + "_antes.jpg"));
                    zos.write(tarefa.getFoto_antes());
                    zos.closeEntry();
                }
                if (tarefa.getFoto_depois() != null && tarefa.getFoto_depois().length > 0) {
                    zos.putNextEntry(new ZipEntry("tarefa_" + tarefa.getId() + "_depois.jpg"));
                    zos.write(tarefa.getFoto_depois());
                    zos.closeEntry();
                }
            }
        } catch (IOException e) {
            System.err.println("Falha ao compactar fotos: " + e.getMessage());
            arquivoZip.delete();
            return; // não apaga nada do banco
        }

        // 2) Envia para o Google Drive
        try {
            String idDrive = enviarParaDrive(arquivoZip);
            System.out.println("Backup enviado ao Drive. ID: " + idDrive);
        } catch (Exception e) {
            System.err.println("Falha ao enviar para o Drive: " + e.getMessage());
            return; // não apaga nada do banco
        } finally {
            arquivoZip.delete();
        }

        // 3) Só limpa o banco depois do upload dar certo
        for (Tarefa tarefa : tarefasComFoto) {
            tarefa.setFoto_antes(null);
            tarefa.setFoto_depois(null);
        }
        tarefaRepository.saveAll(tarefasComFoto);
    }

    private String enviarParaDrive(File arquivo) throws Exception {
        UserCredentials credentials = UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();

        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("ClearSpaces")
                .build();

        com.google.api.services.drive.model.File metadata =
                new com.google.api.services.drive.model.File();
        metadata.setName(arquivo.getName());
        metadata.setParents(List.of(folderId));

        return drive.files()
                .create(metadata, new FileContent("application/zip", arquivo))
                .setFields("id")
                .execute()
                .getId();
    }
}

package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.entity.Tarefa;
import br.com.api.clearSpaces.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class BackupFotosTarefaService {

    private final TarefaRepository tarefaRepository;

    @Value("${DRIVE_SCRIPT_URL}")
    private String scriptUrl;

    @Value("${DRIVE_SCRIPT_SENHA}")
    private String scriptSenha;

    public BackupFotosTarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Scheduled(cron = "0 3 10 * * *", zone = "America/Sao_Paulo")
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
            String resposta = enviarParaDrive(arquivoZip);
            System.out.println("Backup enviado ao Drive. " + resposta);
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
        String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(arquivo.toPath()));
        String json = "{\"senha\":\"" + scriptSenha + "\",\"nome\":\"" + arquivo.getName()
                + "\",\"conteudo\":\"" + base64 + "\"}";

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(scriptUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200 || !resp.body().startsWith("ok:")) {
            throw new IOException("Resposta inesperada: " + resp.statusCode() + " " + resp.body());
        }
        return resp.body();
    }
}

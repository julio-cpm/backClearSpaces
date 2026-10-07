package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.entity.Tarefa;
import br.com.api.clearSpaces.repository.TarefaRepository;
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

    public BackupFotosTarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Scheduled(cron = "0 0 12 * * *")
    @Transactional
    public void realizarBackupELimpezaFotos() {
        List<Tarefa> tarefasComFoto = tarefaRepository.buscarTarefasComFotos();

        if (tarefasComFoto.isEmpty()) {
            return;
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File pastaBackup = new File("../../backups_tarefas");
        if (!pastaBackup.exists()) {
            pastaBackup.mkdirs();
        }
        File arquivoZip = new File(pastaBackup, "fotos_tarefas_" + timestamp + ".zip");
        try (FileOutputStream fos = new FileOutputStream(arquivoZip);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (Tarefa tarefa : tarefasComFoto) {
                if (tarefa.getFoto_antes() != null && tarefa.getFoto_antes().length > 0) {
                    ZipEntry entryAntes = new ZipEntry("tarefa_" + tarefa.getId() + "_antes.jpg");
                    zos.putNextEntry(entryAntes);
                    zos.write(tarefa.getFoto_antes());
                    zos.closeEntry();
                }
                if (tarefa.getFoto_depois() != null && tarefa.getFoto_depois().length > 0) {
                    ZipEntry entryDepois = new ZipEntry("tarefa_" + tarefa.getId() + "_depois.jpg");
                    zos.putNextEntry(entryDepois);
                    zos.write(tarefa.getFoto_depois());
                    zos.closeEntry();
                }
                tarefa.setFoto_antes(null);
                tarefa.setFoto_depois(null);
            }
            tarefaRepository.saveAll(tarefasComFoto);
            System.out.println("Backup concluído com sucesso. Fotos salvas em: " + arquivoZip.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Falha ao compactar fotos: " + e.getMessage());
        }
    }
}
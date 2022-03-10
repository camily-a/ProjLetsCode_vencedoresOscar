package br.com.letscode.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeituraArquivo {

    private List<Oscar> lista = new ArrayList<>();

    public LeituraArquivo(String filePath) {
        if(filePath.equals("listaCompleta")) {
            lista.addAll(LerArquivoCsv("files/oscar_age_male.csv"));
            lista.addAll(LerArquivoCsv("files/oscar_age_female.csv"));
        } else {
            lista = LerArquivoCsv(filePath);
        }
    }


    private List<Oscar> LerArquivoCsv(String path) {
        List<Oscar> lista = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Path.of(path))) {
            return lines.skip(1)
                    .map(Oscar::fromLine)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Oscar> getlista() {
        return Collections.unmodifiableList(this.lista);
    }

}

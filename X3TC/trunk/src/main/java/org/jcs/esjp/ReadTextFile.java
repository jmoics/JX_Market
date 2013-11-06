package org.jcs.esjp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReadTextFile
{
    public final static Charset ENCODING = StandardCharsets.UTF_8;

    // For larger files
    public void readLargerTextFile(final String aFileName)
        throws IOException
    {
        final Path path = Paths.get(aFileName);
        try (Scanner scanner = new Scanner(path, ENCODING.name())) {
            while (scanner.hasNextLine()) {
                // process each line in some way
                log(scanner.nextLine());
            }
        }
    }

    public void readLargerTextFileAlternate(final String aFileName)
        throws IOException
    {
        final Path path = Paths.get(aFileName);
        try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // process each line in some way
                log(line);
            }
        }
    }

    public void writeLargerTextFile(final String aFileName,
                                    final List<String> aLines)
        throws IOException
    {
        final Path path = Paths.get(aFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)) {
            for (final String line : aLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static void log(final Object aMsg)
    {
        System.out.println(String.valueOf(aMsg));
    }

}

package me.itzwam.xmlmerge;

import org.atteo.xmlcombiner.XmlCombiner;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, TransformerException {
        if (args.length < 3) { /* Print usage if args are less than 3 */
            String jarname = new java.io.File(Main.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            System.out.println(String.format("[#] Usage : %s <file1.xml> <file2.xml> ... <fileN.xml> <out.xml>", jarname));
            exit(1);
        }

        // create XmlCombiner object
        XmlCombiner combiner = null;
        try {
            combiner = new XmlCombiner();
        } catch (Exception e) {
            e.printStackTrace();
            exit(1);
        }


        for (int i = 0; i <= args.length - 2; i++) { /* loop trough arguments (except last) */
            String path = args[i];
            File file = new File(path); // Create a file object with path = arg
            System.out.println(String.format("[#] Merging : '%s'", file.getPath()));
            combiner.combine(file.toPath()); // Combine this file in the XML collection
        }

        File output = new File(args[args.length - 1]);
        combiner.buildDocument(output.toPath()); // Put the XML collection in the output file
        System.out.println();
        System.out.println(String.format("[#] Destination file : %s", output.getPath()));

    }
}

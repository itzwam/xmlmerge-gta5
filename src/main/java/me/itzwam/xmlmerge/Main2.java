package me.itzwam.xmlmerge;

import org.atteo.xmlcombiner.XmlCombiner;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class Main2 {
    public static void main(String[] args) throws IOException, SAXException, TransformerException {
        if (args.length != 1) { /* Print usage if there is not 1 arg */
            String jarname = new File(Main2.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            System.out.println(String.format("[#] Usage : %s <workdir>", jarname));
            exit(1);
        }

        // create XmlCombiner object


        File all = new File(args[0] + File.separator + "zz_empty");
        String[] xmls = all.list((current, name) -> (new File(current, name).isFile() && new File(current, name).getName().endsWith("meta") || new File(current, name).getName().endsWith("xml")));

        File workdir = new File(args[0]);
        String[] directories = workdir.list((current, name) -> (new File(current, name).isDirectory() && !new File(current, name).getName().equals("00_all") && !new File(current, name).getName().equals("zz_empty")));

        for (String xml : xmls) {

            XmlCombiner combiner = null;
            try {
                combiner = new XmlCombiner();
            } catch (Exception e) {
                e.printStackTrace();
                exit(1);
            }

            File empty = new File(workdir + File.separator + "zz_empty" + File.separator + xml);
            if (empty.exists()) {
                System.out.println(String.format("[#] Creating %s", empty.getName()));
                combiner.combine(empty.toPath()); // Combine this file in the XML collection
            }

            for (String dir : directories) {
                File file = new File(workdir + File.separator + dir + File.separator + xml);
                if (file.exists()) {
                    System.out.println(String.format("[#] Merging %s", file.getPath()));
                    combiner.combine(file.toPath()); // Combine this file in the XML collection
                }
            }

            File output = new File(workdir + File.separator + "00_all" + File.separator + xml);
            System.out.println(String.format("[#] Destination file is %s", output.getPath()));
            combiner.buildDocument(output.toPath()); // Put the XML collection in the output file
            System.out.println();
        }
    }
}

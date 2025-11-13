package coverage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Lee el reporte XML de JaCoCo (jacoco.xml) y genera un archivo de texto
 * con las líneas cubiertas y no cubiertas por clase.
 *
 * Uso:
 *   java coverage.CoverageReportGenerator <inputJacocoXml> <outputTxt>
 *
 * Este utilitario se ejecuta desde Maven con exec-maven-plugin en la fase verify.
 */
public class CoverageReportGenerator {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Uso: CoverageReportGenerator <inputJacocoXml> <outputTxt>");
            return;
        }
        String input = args[0];
        String output = args[1];

        File xmlFile = new File(input);
        if (!xmlFile.exists()) {
            System.out.println("No se encontró el archivo jacoco.xml en: " + xmlFile.getAbsolutePath());
            return;
        }

        Map<String, ClassCoverage> coverageByClass = parseJacocoXml(xmlFile);
        writeReport(output, coverageByClass);

        System.out.println("Archivo de líneas de cobertura generado en: " + output);
    }

    private static Map<String, ClassCoverage> parseJacocoXml(File xmlFile) throws Exception {
        Map<String, ClassCoverage> result = new LinkedHashMap<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList packageNodes = doc.getElementsByTagName("package");
        for (int i = 0; i < packageNodes.getLength(); i++) {
            Node pkgNode = packageNodes.item(i);
            if (pkgNode.getNodeType() != Node.ELEMENT_NODE) continue;
            Element pkgEl = (Element) pkgNode;
            String pkgName = pkgEl.getAttribute("name");

            NodeList classNodes = pkgEl.getElementsByTagName("class");
            for (int j = 0; j < classNodes.getLength(); j++) {
                Node clsNode = classNodes.item(j);
                if (clsNode.getNodeType() != Node.ELEMENT_NODE) continue;
                Element clsEl = (Element) clsNode;
                String className = clsEl.getAttribute("name");
                String fqcn = (pkgName != null && !pkgName.isEmpty()) ? (pkgName + "." + className) : className;

                ClassCoverage cc = new ClassCoverage(fqcn);

                NodeList lines = clsEl.getElementsByTagName("line");
                for (int k = 0; k < lines.getLength(); k++) {
                    Element lineEl = (Element) lines.item(k);
                    int nr = Integer.parseInt(lineEl.getAttribute("nr"));
                    int mi = Integer.parseInt(lineEl.getAttribute("mi")); // missed instructions
                    int ci = Integer.parseInt(lineEl.getAttribute("ci")); // covered instructions

                    if (ci > 0) {
                        cc.covered.add(nr);
                    } else if (mi > 0) {
                        cc.missed.add(nr);
                    } else {
                        // líneas sin código ejecutable pueden tener mi=0 y ci=0; las ignoramos
                    }
                }

                result.put(fqcn, cc);
            }
        }
        return result;
    }

    private static void writeReport(String output, Map<String, ClassCoverage> coverageByClass) throws Exception {
        File outFile = new File(output);
        outFile.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(outFile, StandardCharsets.UTF_8)) {
            fw.write("Cobertura por clase (líneas cubiertas y no cubiertas)\n");
            fw.write("===============================================================================\n\n");
            for (Map.Entry<String, ClassCoverage> e : coverageByClass.entrySet()) {
                ClassCoverage cc = e.getValue();
                fw.write("Clase: " + cc.fqcn + "\n");
                fw.write("  Líneas cubiertas (" + cc.covered.size() + "): " + cc.covered + "\n");
                fw.write("  Líneas NO cubiertas (" + cc.missed.size() + "): " + cc.missed + "\n\n");
            }
        }
    }

    private static class ClassCoverage {
        final String fqcn;
        final List<Integer> covered = new ArrayList<>();
        final List<Integer> missed = new ArrayList<>();
        ClassCoverage(String fqcn) { this.fqcn = fqcn; }
    }
}

import argo.jdom.JdomParser;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import static spark.Spark.*;
import LOM.LOM;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Lucho on 18/04/2016.
 * 523dd212d123-3432-5ds43asdgg345gs
 */

public class Main {


    public static void main(String [] args) throws InvalidSyntaxException {
        BasicConfigurator.configure();
        port(9090);
        post("/", (request, response) -> {
            JdomParser JDOM_PARSER = new JdomParser();
            JsonRootNode json = JDOM_PARSER.parse(request.body());
            //JSONObject tObject = new JSONObject();
            return modelToJson(matching(json));
        });
        //JSONObject test = new JSONObject(modelToJson(matching(new JSONObject(request))));
        //System.out.println(json.getArrayNode("lom").get(0).getNode("general").getNode("coverage").getText());
//        JdomParser JDOM_PARSER = new JdomParser();
//        JsonRootNode json = JDOM_PARSER.parse(request);
//        //System.out.println(json.getArrayNode("lom").get(0).getNode("general").getNode("identifier").getNode("catalog").getText());
//        System.out.println(modelToJson(matching(json)));
        //SimilarityMeasures sm = new SimilarityMeasures();
        //System.out.println(sm.similarText("hasEntry","entryis"));
    }

    public static String test(){
        String string_query = "select distinct ?Concept where {[] a ?Concept} LIMIT 100";//--subclases

        String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n "+
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX map: <http://localhost:2020/resource/#>\n" +
                "PREFIX terms: <http://purl.org/dc/terms/>\n" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>";

        String endpoint = "http://52.33.28.221:8890/sparql";

        Query queryObj = QueryFactory.create(prefix + string_query);
        //QueryExecution queryEx = QueryExecutionFactory.sparqlService(endpoint,queryObj,ontologia_IRI);
        QueryExecution queryEx = QueryExecutionFactory.sparqlService(endpoint,queryObj);

        ResultSet resultSet = queryEx.execSelect();
        return "<p>"+ResultSetFormatter.asText(resultSet,queryObj)+"</p>";
    }

    public static String matching(JsonRootNode json) throws IOException {
        LOM lom = new LOM(json.getArrayNode("lom").get(0),json.getNode("externEndpURL").getText(),json.getNode("uriGraph").getText());
//        lom.match();
//        save(lom.getLom());
        //return lom.getTechnical().getRequirement().getOrComposite().getName().toString();
        Map response = lom.match();
        OntModel test = (OntModel) response.get("model") ;

        FileWriter out = null;
        try {
            // XML format - long and verbose
            out = new FileWriter( "mymodel.xml" );
            test.write( out, "RDF/XML-ABBREV" );

            // OR Turtle format - compact and more readable
            // use this variant if you're not sure which to use!
            out = new FileWriter( "mymodel.ttl" );
            test.write( out, "Turtle" );
        }
        finally {
            if (out != null) {
                try {out.close();} catch (IOException ignore) {}
            }
        }

        JSONObject Json = new JSONObject();
        Map<String,String> t = new HashMap<>();
        t.put("test","holy Fuck");
        Json.put("lom",t);

        return response.get("jsonObject").toString();
    }

    public static String save(OntModel lom){
        //...
        return "DATASET";
    }

    public static String modelToJson(String model){
        // ..
        //return "RESULTADOS DEL MATCHING";
        return model;
    }
}

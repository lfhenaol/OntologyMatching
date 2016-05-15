package Ontology;

import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.tdb.store.Hash;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import similarityMeasures.SimilarityMeasures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.jena.assembler.JA.Model;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Ontology {
    private String name;
    private String endPointURL;
    private String graph;

    public Ontology(){

    }

    public Map queryProperty(Resource typeProperty, ArrayList<Resource> localProperty){
        SelectBuilder queryBuild = new SelectBuilder();
        queryBuild.addVar("*");
        queryBuild.addWhere("?a", RDF.type, typeProperty);
        Query query = queryBuild.build();
        QueryExecution execQProperty = QueryExecutionFactory.sparqlService(this.endPointURL, query, this.graph);
        ResultSet select = execQProperty.execSelect();

        Map<Resource,Object> measure = new HashMap<>();
        Map<Resource,Object> similar = new HashMap<>();
        for (Resource aLocalProperty : localProperty) {
            measure.put(aLocalProperty, (float) 0);
            similar.put(aLocalProperty, "No matches");
        }

        Resource val = null;
        Map<Object, Map> result = new HashMap<>();
        SimilarityMeasures sm = new SimilarityMeasures();
        if(select.hasNext()) {
            while (select.hasNext()) {
                QuerySolution querySol = select.next();
                Resource resourceQuery = querySol.getResource("?a");
                for (Resource aLocalProperty : localProperty) {
                    float similarValue = sm.similarText(resourceQuery.getLocalName(), aLocalProperty.getLocalName());
                    if (similarValue > ((float) measure.get(aLocalProperty))) {
                        measure.put(aLocalProperty, similarValue);
                        similar.put(aLocalProperty, resourceQuery);
                    }
                }
            }

            result.put("measure",measure);
            result.put("resource",similar);
        } else {
            result.put("measure",measure);
            result.put("resource",similar);
        }
        execQProperty.close();

        return result;
    }

    public Map getSimilarity(ArrayList<Resource> localProperty ){
        Map<Resource,Resource> getSim = new HashMap<>();
        OntModel resp = ModelFactory.createOntologyModel();
        resp.createResource("http://www.example.com/No-Matches");

        ArrayList<Resource> locProp = (ArrayList<Resource>) localProperty.clone();

        for(int i=locProp.size()-1; i > -1 ; i--) {
            if (localProperty.get(i).getLocalName().equals("undefined")) {
                getSim.put(locProp.get(i),locProp.get(i));
                locProp.remove(i);
            }
        }

        Map qpDataProp = new HashMap<>();
        Map qpObjectProp = new HashMap<>();
        if(locProp.size() > 0) {
            qpDataProp = this.queryProperty(OWL.DatatypeProperty, locProp);
            qpObjectProp = this.queryProperty(OWL.ObjectProperty, locProp);
        }

        for (int j=0;j < locProp.size();j++){
            HashMap hmOP = (HashMap) qpObjectProp.get("measure");
            HashMap hmDP = (HashMap) qpDataProp.get("measure");
            if((((float)hmOP.get(locProp.get(j)))
                    >= ((float)hmDP.get(locProp.get(j))))
                    && (((float)hmDP.get(locProp.get(j)))
                    != 0)) {

                hmOP = (HashMap) qpObjectProp.get("resource");
                getSim.put(locProp.get(j),(Resource) hmOP.get(locProp.get(j)));

            } else if((((float)hmDP.get(locProp.get(j)))
                    > ((float)hmOP.get(locProp.get(j))))){

                hmDP = (HashMap) qpDataProp.get("resource");
                getSim.put(locProp.get(j),(Resource) hmDP.get(locProp.get(j)));

            } else{
                getSim.put(locProp.get(j), (Resource) resp);
            }
        }

        return getSim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndPointURL() {
        return endPointURL;
    }

    public void setEndPointURL(String endPointURL) {
        this.endPointURL = endPointURL;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }
}

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

    public Map queryProperty(Resource typeProperty, Resource localProperty){
        SelectBuilder queryBuild = new SelectBuilder();
        queryBuild.addVar("*");
        queryBuild.addWhere("?a", RDF.type, typeProperty);
        Query query = queryBuild.build();
        QueryExecution execQProperty = QueryExecutionFactory.sparqlService(this.endPointURL, query, this.graph);
        ResultSet select = execQProperty.execSelect();
        float measure = 0;
        Resource val = null;
        Map<Object, Object> result = new HashMap<>();
        SimilarityMeasures sm = new SimilarityMeasures();
        if(select.hasNext()) {
            while (select.hasNext()) {
                QuerySolution querySol = select.next();
                Resource resourceQuery = querySol.getResource("?a");
                float aux = sm.similarText(resourceQuery.getLocalName(), localProperty.getLocalName());
                if (aux > measure) {
                    measure = aux;
                    val = resourceQuery;
                }

            }

            result.put("measure",measure);
            result.put("resource",val);
        } else {
            measure = 0;
            String noMatch = "No matches";
            result.put("measure",measure);
            result.put("resource",noMatch);
        }
        execQProperty.close();

        return result;
    }

    public Resource getSimilarity(Resource localProperty){
        if (!localProperty.getLocalName().equals("undefined")) {
            Map qpDataProp = this.queryProperty(OWL.DatatypeProperty, localProperty);
            Map qpObjectProp = this.queryProperty(OWL.ObjectProperty, localProperty);
            float measureDataProp = (float) qpDataProp.get("measure");
            float measureObjectProp = (float) qpObjectProp.get("measure");
            if ((measureObjectProp >= measureDataProp) && (measureDataProp != 0)) {
                return (Resource) qpObjectProp.get("resource");
            }else if(measureDataProp > measureObjectProp ) {
                return (Resource) qpDataProp.get("resource");
            }
            OntModel resp = ModelFactory.createOntologyModel();
            return resp.createResource("http://www.example.com/No-Matches");
        } else{
            return localProperty;
        }
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

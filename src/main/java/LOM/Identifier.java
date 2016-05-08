package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.graph.Node;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.QueryEngineFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.json.JSONObject;
import similarityMeasures.SimilarityMeasures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Identifier {
    private Resource catalog;
    private Resource entry;
    private OntModel identifier;

    Identifier(JsonNode jsonIdentifier){
       this.identifier = ModelFactory.createOntologyModel();
       this.catalog = this.undefinedToResource(jsonIdentifier.getNode("catalog").getText());
       this.entry = this.undefinedToResource(jsonIdentifier.getNode("entry").getText());

    }

    public Map match(Ontology onto){
        // Consultas especificas de catalog y entry por dataproperty y objectproperty
        //QueryExecution execObjectPropery = onto.queryProperty(OWL.ObjectProperty);
        //evaluar por similaridad cada resultado, en busca del elemento que posea myor porcentaje de similaridad
        //a entry y catalog
        // Evaluar posible learning *
        //Luego de otener los resultados más cercanos, hacer el emparejamiento
        //gardar modelo en memoria

        Resource entryExtern = onto.getSimilarity(this.entry);
        Resource catalogExtern = onto.getSimilarity(this.catalog);

        this.identifier.createObjectProperty(entryExtern.toString());
        this.identifier.createObjectProperty(catalogExtern.toString());
        this.identifier.createObjectProperty(this.entry.toString());
        this.identifier.createObjectProperty(this.catalog.toString());

        this.identifier.createSymmetricProperty(this.entry.toString());
        this.identifier.createSymmetricProperty(this.catalog.toString());

        this.identifier.add(this.entry,OWL.equivalentProperty,entryExtern);
        this.identifier.add(this.catalog,OWL.equivalentProperty,catalogExtern);

        JSONObject json = new JSONObject("{\n" +
                "          \"catalog\": \"" +((this.catalog.getLocalName().equals("undefined"))?"Unselected":((catalogExtern.getLocalName().equals("No-Matches"))?"No matches":catalogExtern)) + "\",\n" +
                "          \"entry\": \"" + ((this.entry.getLocalName().equals("undefined"))?"Unselected":((entryExtern.getLocalName().equals("No-Matches"))?"No matches":entryExtern)) + "\"\n" +
                "        }");

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.identifier);
        response.put("jsonObject",json);
        return response;
    }

    private Resource undefinedToResource(String localProperty){
        String uri = "http://www.example.com/undefined";
        return (localProperty.equals("undefined"))? this.identifier.createResource(uri) : this.identifier.createResource(localProperty);
    }

    public Resource getCatalog() {
        return catalog;
    }

    public void setCatalog(Resource catalog) {
        this.catalog = catalog;
    }

    public Resource getEntry() {
        return entry;
    }

    public void setEntry(Resource entry) {
        this.entry = entry;
    }

    public OntModel getIdentifier() {
        return identifier;
    }

    public void setIdentifier(OntModel identifier) {
        this.identifier = identifier;
    }
}

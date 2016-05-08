package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.QueryEngineFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import similarityMeasures.SimilarityMeasures;

import java.util.Iterator;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Identifier {
    private Resource catalog;
    private Resource entry;
    private OntModel identifier;

    Identifier(JsonNode jsonIdentifier){
       this.identifier = ModelFactory.createOntologyModel();
       this.catalog = this.identifier.createResource(jsonIdentifier.getNode("catalog").getText());
       this.entry = this.identifier.createResource(jsonIdentifier.getNode("entry").getText());

    }

    public OntModel match(Ontology onto){
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
        this.identifier.add(this.entry,OWL.equivalentProperty,entryExtern);
        this.identifier.add(this.catalog,OWL.equivalentProperty,catalogExtern);

        return this.identifier;
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

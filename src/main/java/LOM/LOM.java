package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import Ontology.Ontology;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * Created by Lucho on 18/04/2016.
 */
public class LOM extends Ontology {
    private General general;
    private Technical technical;
    private Educational educational;
    private Classification classification;
    private OntModel lomModel;

    public LOM(JsonNode json, String externEndpURL, String uriGraph){
        this.lomModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        this.general = new General(json.getNode("general"));
        this.technical = new Technical(json.getNode("technical"));
        this.educational = new Educational(json.getNode("educational"));
        this.classification = new Classification(json.getNode("classification"));

        super.setEndPointURL(externEndpURL);
        super.setGraph(uriGraph);
    }

    public OntModel match(){
       return this.general.match(this);
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }

    public Educational getEducational() {
        return educational;
    }

    public void setEducational(Educational educational) {
        this.educational = educational;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public OntModel getLom() {
        return lomModel;
    }

    public void setLom(OntModel lom) {
        this.lomModel = lom;
    }
}

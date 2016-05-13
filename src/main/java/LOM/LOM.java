package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import Ontology.Ontology;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import java.util.HashMap;
import java.util.Map;

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

    public Map match(){
        Map general = this.general.match(this);
        Map technical = this.technical.match(this);
        Map educational = this.educational.match(this);
        Map classification = this.classification.match(this);

        this.lomModel.union(this.general.getGeneralModel());
        this.lomModel.union(this.technical.getTechnicalModel());
        this.lomModel.union(this.educational.getEducationalModel());
        this.lomModel.union(this.classification.getClassificationModel());

        String json = "{\"lom\":[{\"general\":"+general.get("jsonObject").toString()+",\"technical\":" +
                technical.get("jsonObject").toString()+",\"educational\":"+educational.get("jsonObject").toString()+
                ",\"classification\":"+classification.get("jsonObject").toString()+"}]}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.lomModel);
        response.put("jsonObject",json);
        return response;
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

    public OntModel getLomModel() {
        return lomModel;
    }

    public void setLomModel(OntModel lom) {
        this.lomModel = lom;
    }
}

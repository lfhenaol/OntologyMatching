package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucho on 18/04/2016.
 */
public class General {
    private Identifier identifier;
    private Resource title;
    private Resource language;
    private Resource description;
    private Resource keyword;
    private Resource coverage;
    private Resource structure;
    private Resource aggregationLevel;
    private OntModel generalModel;

    public General(JsonNode jsonGeneral){
        this.generalModel = ModelFactory.createOntologyModel();
        this.title = this.undefinedToResource(jsonGeneral.getNode("title").getText());
        this.language = this.undefinedToResource(jsonGeneral.getNode("language").getText());
        this.description = this.undefinedToResource(jsonGeneral.getNode("description").getText());
        this.keyword = this.undefinedToResource(jsonGeneral.getNode("keyword").getText());
        this.coverage = this.undefinedToResource(jsonGeneral.getNode("coverage").getText());
        this.structure = this.undefinedToResource(jsonGeneral.getNode("structure").getText());
        this.aggregationLevel = this.undefinedToResource(jsonGeneral.getNode("aggregationLevel").getText());

        this.identifier = new Identifier(jsonGeneral.getNode("identifier"));
    }

    public Map match(Ontology onto) {
        ArrayList<Resource> localProp = new ArrayList<>();
        localProp.add(this.title);
        localProp.add(this.language);
        localProp.add(this.description);
        localProp.add(this.keyword);
        localProp.add(this.coverage);
        localProp.add(this.structure);
        localProp.add(this.aggregationLevel);

        ArrayList<String> jresponse = new ArrayList<>();
        jresponse.add("title");
        jresponse.add("language");
        jresponse.add("description");
        jresponse.add("keyword");
        jresponse.add("coverage");
        jresponse.add("structure");
        jresponse.add("aggregationLevel");
        String json = "{\n";
        Map similar = onto.getSimilarity(localProp);
        for (int i = 0; i<localProp.size();i++) {
            Resource rs = (Resource) similar.get(localProp.get(i));
            Resource lc = localProp.get(i);
            this.generalModel.createObjectProperty(rs.toString());
            this.generalModel.createObjectProperty(lc.toString());
            this.generalModel.createSymmetricProperty(lc.toString());
            this.generalModel.add(lc, OWL.equivalentProperty, rs);
            json = json + "\""+jresponse.get(i)+"\": \"" + ((lc.getLocalName().equals("undefined"))? "Unselected":((rs.getLocalName().equals("No-Matches"))? "No matches": rs.toString()))+"\"";
            if(i < (localProp.size()-1)){
                json = json + ",";
            }
        }
        Map result = this.identifier.match(onto);
        this.generalModel.union(this.identifier.getIdentifier());
        json = json +",\"identifier\":" +result.get("jsonObject").toString() + "}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.generalModel);
        response.put("jsonObject",json);
        return response;
    }

    private Resource undefinedToResource(String localProperty){
        String uri = "http://www.example.com/undefined";
        return (localProperty.equals("undefined"))? this.generalModel.createResource(uri) : this.generalModel.createResource(localProperty);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Resource getTitle() {
        return title;
    }

    public void setTitle(Resource title) {
        this.title = title;
    }

    public Resource getLanguage() {
        return language;
    }

    public void setLanguage(Resource language) {
        this.language = language;
    }

    public Resource getDescription() {
        return description;
    }

    public void setDescription(Resource description) {
        this.description = description;
    }

    public Resource getKeyword() {
        return keyword;
    }

    public void setKeyword(Resource keyword) {
        this.keyword = keyword;
    }

    public Resource getCoverage() {
        return coverage;
    }

    public void setCoverage(Resource coverage) {
        this.coverage = coverage;
    }

    public Resource getStructure() {
        return structure;
    }

    public void setStructure(Resource structure) {
        this.structure = structure;
    }

    public Resource getAggregationLevel() {
        return aggregationLevel;
    }

    public void setAggregationLevel(Resource aggregationLevel) {
        this.aggregationLevel = aggregationLevel;
    }

    public OntModel getGeneralModel() {
        return generalModel;
    }

    public void setGeneralModel(OntModel general) {
        this.generalModel = general;
    }
}

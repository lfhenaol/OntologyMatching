package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Educational {
    private Resource interactivityType;
    private Resource learningResourceType;
    private Resource interactivityLevel;
    private Resource semanticDensity;
    private Resource intendedEndUserRole;
    private Resource context;
    private Resource typicalAgeRange;
    private Resource difficulty;
    private Resource typicalLearningTime;
    private Resource description;
    private Resource language;
    private OntModel educationalModel;

    Educational(JsonNode jsonEducational){
        this.educationalModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        this.interactivityType = this.undefinedToResource(jsonEducational.getNode("interactivityType").getText());
        this.learningResourceType = this.undefinedToResource(jsonEducational.getNode("learningResourceType").getText());
        this.interactivityLevel = this.undefinedToResource(jsonEducational.getNode("interactivityLevel").getText());
        this.semanticDensity = this.undefinedToResource(jsonEducational.getNode("semanticDensity").getText());
        this.intendedEndUserRole = this.undefinedToResource(jsonEducational.getNode("intendedEndUserRole").getText());
        this.context = this.undefinedToResource(jsonEducational.getNode("context").getText());
        this.typicalAgeRange = this.undefinedToResource(jsonEducational.getNode("typicalAgeRange").getText());
        this.difficulty = this.undefinedToResource(jsonEducational.getNode("difficulty").getText());
        this.typicalLearningTime = this.undefinedToResource(jsonEducational.getNode("typicalLearningTime").getText());
        this.description = this.undefinedToResource(jsonEducational.getNode("description").getText());
        this.language = this.undefinedToResource(jsonEducational.getNode("language").getText());
    }

    public Map match(Ontology onto){
        ArrayList<Resource> localProp = new ArrayList<>();
        localProp.add(this.interactivityType);
        localProp.add(this.learningResourceType);
        localProp.add(this.interactivityLevel);
        localProp.add(this.semanticDensity);
        localProp.add(this.intendedEndUserRole);
        localProp.add(this.context);
        localProp.add(this.typicalAgeRange);
        localProp.add(this.difficulty);
        localProp.add(this.typicalLearningTime);
        localProp.add(this.description);
        localProp.add(this.language);

        ArrayList<String> jresponse = new ArrayList<>();
        jresponse.add("interactivityType");
        jresponse.add("learningResourceType");
        jresponse.add("interactivityLevel");
        jresponse.add("semanticDensity");
        jresponse.add("intendedEndUserRole");
        jresponse.add("context");
        jresponse.add("typicalAgeRange");
        jresponse.add("difficulty");
        jresponse.add("typicalLearningTime");
        jresponse.add("description");
        jresponse.add("language");

        String json = "{\n";
        Map similar = onto.getSimilarity(localProp);
        for (int i = 0; i<localProp.size();i++) {
            Resource rs = (Resource) similar.get(localProp.get(i));
            Resource lc = localProp.get(i);
            this.educationalModel.createObjectProperty(rs.toString());
            this.educationalModel.createObjectProperty(lc.toString());
            this.educationalModel.createSymmetricProperty(lc.toString());
            this.educationalModel.add(lc, OWL.equivalentProperty, rs);
            json = json + "\""+jresponse.get(i)+"\": \"" + ((lc.getLocalName().equals("undefined"))? "Unselected":((rs.getLocalName().equals("No-Matches"))? "No matches": rs.toString()))+"\"";
            if(i < (localProp.size()-1)){
                json = json + ",";
            }
        }

        json = json + "}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.educationalModel);
        response.put("jsonObject",json);
        return response;

    }

    private Resource undefinedToResource(String localProperty){
        String uri = "http://www.example.com/undefined";
        return (localProperty.equals("undefined"))? this.educationalModel.createResource(uri) : this.educationalModel.createResource(localProperty);
    }

    public Resource getInteractivityType() {
        return interactivityType;
    }

    public void setInteractivityType(Resource interactivityType) {
        this.interactivityType = interactivityType;
    }

    public Resource getLearningResourceType() {
        return learningResourceType;
    }

    public void setLearningResourceType(Resource learningResourceType) {
        this.learningResourceType = learningResourceType;
    }

    public Resource getInteractivityLevel() {
        return interactivityLevel;
    }

    public void setInteractivityLevel(Resource interactivityLevel) {
        this.interactivityLevel = interactivityLevel;
    }

    public Resource getSemanticDensity() {
        return semanticDensity;
    }

    public void setSemanticDensity(Resource semanticDensity) {
        this.semanticDensity = semanticDensity;
    }

    public Resource getIntendedEndUserRole() {
        return intendedEndUserRole;
    }

    public void setIntendedEndUserRole(Resource intendedEndUserRole) {
        this.intendedEndUserRole = intendedEndUserRole;
    }

    public Resource getContext() {
        return context;
    }

    public void setContext(Resource context) {
        this.context = context;
    }

    public Resource getTypicalAgeRange() {
        return typicalAgeRange;
    }

    public void setTypicalAgeRange(Resource typicalAgeRange) {
        this.typicalAgeRange = typicalAgeRange;
    }

    public Resource getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Resource difficulty) {
        this.difficulty = difficulty;
    }

    public Resource getTypicalLearningTime() {
        return typicalLearningTime;
    }

    public void setTypicalLearningTime(Resource typicalLearningTime) {
        this.typicalLearningTime = typicalLearningTime;
    }

    public Resource getDescription() {
        return description;
    }

    public void setDescription(Resource description) {
        this.description = description;
    }

    public Resource getLanguage() {
        return language;
    }

    public void setLanguage(Resource language) {
        this.language = language;
    }

    public OntModel getEducationalModel() {
        return educationalModel;
    }

    public void setEducationalModel(OntModel educationalModel) {
        this.educationalModel = educationalModel;
    }
}

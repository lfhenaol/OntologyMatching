package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

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
        this.interactivityType = this.educationalModel.createResource(jsonEducational.getNode("interactivityType").getText());
        this.learningResourceType = this.educationalModel.createResource(jsonEducational.getNode("learningResourceType").getText());
        this.interactivityLevel = this.educationalModel.createResource(jsonEducational.getNode("interactivityLevel").getText());
        this.semanticDensity = this.educationalModel.createResource(jsonEducational.getNode("semanticDensity").getText());
        this.intendedEndUserRole = this.educationalModel.createResource(jsonEducational.getNode("intendedEndUserRole").getText());
        this.context = this.educationalModel.createResource(jsonEducational.getNode("context").getText());
        this.typicalAgeRange = this.educationalModel.createResource(jsonEducational.getNode("typicalAgeRange").getText());
        this.difficulty = this.educationalModel.createResource(jsonEducational.getNode("difficulty").getText());
        this.typicalLearningTime = this.educationalModel.createResource(jsonEducational.getNode("typicalLearningTime").getText());
        this.description = this.educationalModel.createResource(jsonEducational.getNode("description").getText());
        this.language = this.educationalModel.createResource(jsonEducational.getNode("language").getText());
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

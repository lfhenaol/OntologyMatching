package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

import java.util.HashMap;

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
        this.title = this.generalModel.createResource(jsonGeneral.getNode("title").getText());
        this.language = this.generalModel.createResource(jsonGeneral.getNode("language").getText());
        this.description = this.generalModel.createResource(jsonGeneral.getNode("description").getText());
        this.keyword = this.generalModel.createResource(jsonGeneral.getNode("keyword").getText());
        this.coverage = this.generalModel.createResource(jsonGeneral.getNode("coverage").getText());
        this.structure = this.generalModel.createResource(jsonGeneral.getNode("structure").getText());
        this.aggregationLevel = this.generalModel.createResource(jsonGeneral.getNode("aggregationLevel").getText());

        this.identifier = new Identifier(jsonGeneral.getNode("identifier"));
    }

    public OntModel match(Ontology onto) {

        //return this.generalModel;
        return this.identifier.match(onto);
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

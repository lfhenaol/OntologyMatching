package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Classification {
    private Resource purpose;
    private TaxonPath taxonPath;
    private Resource description;
    private Resource keyword;
    private OntModel classificationModel;

    Classification(JsonNode jsonClassification){
        this.classificationModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        this.purpose = this.classificationModel.createResource(jsonClassification.getNode("purpose").getText());
        this.taxonPath = new TaxonPath(jsonClassification.getNode("taxonPath"));
        this.description = this.classificationModel.createResource(jsonClassification.getNode("description").getText());
        this.keyword = this.classificationModel.createResource(jsonClassification.getNode("keyword").getText());

    }

    public Resource getPurpose() {
        return purpose;
    }

    public void setPurpose(Resource purpose) {
        this.purpose = purpose;
    }

    public TaxonPath getTaxonPath() {
        return taxonPath;
    }

    public void setTaxonPath(TaxonPath taxonPath) {
        this.taxonPath = taxonPath;
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

    public OntModel getClassification() {
        return classificationModel;
    }

    public void setClassification(OntModel classification) {
        this.classificationModel = classification;
    }
}

package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Taxon {
    private Resource id;
    private Resource entry;
    private OntModel taxonModel;
    Taxon(JsonNode jsonTaxon){
        this.taxonModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        this.id = this.taxonModel.createResource(jsonTaxon.getNode("id").getText());
        this.entry = this.taxonModel.createResource(jsonTaxon.getNode("entry").getText());
    }

    public Resource getId() {
        return id;
    }

    public void setId(Resource id) {
        this.id = id;
    }

    public Resource getEntry() {
        return entry;
    }

    public void setEntry(Resource entry) {
        this.entry = entry;
    }

    public OntModel getTaxon() {
        return taxonModel;
    }

    public void setTaxon(OntModel taxon) {
        this.taxonModel = taxon;
    }
}

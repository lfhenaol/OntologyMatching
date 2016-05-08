package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 * Created by Lucho on 18/04/2016.
 */
public class TaxonPath {
    private Resource source;
    private Taxon taxon;
    private OntModel taxonPathModel;

    TaxonPath(JsonNode jsonTaxonPath){
        this.taxonPathModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        this.source = taxonPathModel.createResource(jsonTaxonPath.getNode("source").getText());
        this.taxon = new Taxon(jsonTaxonPath.getNode("taxon"));
    }

    public Resource getSource() {
        return source;
    }

    public void setSource(Resource source) {
        this.source = source;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }

    public OntModel getTaxonPath() {
        return taxonPathModel;
    }

    public void setTaxonPath(OntModel taxonPath) {
        this.taxonPathModel = taxonPath;
    }
}

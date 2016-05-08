package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 * Created by Lucho on 18/04/2016.
 */
public class OrComposite {
    private Resource type;
    private Resource name;
    private Resource minimumVersion;
    private Resource maximumVersion;
    private OntModel orCompositeModel;

    OrComposite(JsonNode jsonOrComposite){
        this.orCompositeModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        this.name = this.orCompositeModel.createResource(jsonOrComposite.getNode("name").getText());
        this.type = this.orCompositeModel.createResource(jsonOrComposite.getNode("type").getText());
        this.minimumVersion = this.orCompositeModel.createResource(jsonOrComposite.getNode("minimumVersion").getText());
        this.maximumVersion = this.orCompositeModel.createResource(jsonOrComposite.getNode("maximumVersion").getText());
    }

    public Resource getType() {
        return type;
    }

    public void setType(Resource type) {
        this.type = type;
    }

    public Resource getName() {
        return name;
    }

    public void setName(Resource name) {
        this.name = name;
    }

    public Resource getMinimumVersion() {
        return minimumVersion;
    }

    public void setMinimumVersion(Resource minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    public Resource getMaximumVersion() {
        return maximumVersion;
    }

    public void setMaximumVersion(Resource maximumVersion) {
        this.maximumVersion = maximumVersion;
    }

    public OntModel getOrCompositeModel() {
        return orCompositeModel;
    }

    public void setOrCompositeModel(OntModel orComposite) {
        orCompositeModel = orComposite;
    }
}

package LOM;

import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Requirement {
    private OrComposite orComposite;
    private OntModel requirementModel;

    Requirement(JsonNode jsonRequirement){
        this.requirementModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        this.orComposite = new OrComposite(jsonRequirement.getNode("orComposite"));
    }

    public OrComposite getOrComposite() {
        return orComposite;
    }

    public void setOrComposite(OrComposite orComposite) {
        this.orComposite = orComposite;
    }

    public OntModel getRequirementModel() {
        return requirementModel;
    }

    public void setRequirementModel(OntModel requirement) {
        this.requirementModel = requirement;
    }
}

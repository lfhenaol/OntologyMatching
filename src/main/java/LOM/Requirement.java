package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucho on 18/04/2016.
 */
public class Requirement {
    private OrComposite orComposite;
    private OntModel requirementModel;

    Requirement(JsonNode jsonRequirement){
        this.requirementModel = ModelFactory.createOntologyModel();
        this.orComposite = new OrComposite(jsonRequirement.getNode("orComposite"));
    }

    public Map match(Ontology onto){
        Map result = this.orComposite.match(onto);
        this.requirementModel.union(this.orComposite.getOrCompositeModel());
        String json = "{\"orComposite\": " + result.get("jsonObject").toString()+ "}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.requirementModel);
        response.put("jsonObject",json);
        return response;
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

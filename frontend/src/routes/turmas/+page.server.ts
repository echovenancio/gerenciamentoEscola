import type { PageServerLoad, Actions } from "./$types.js";
import { fail } from "@sveltejs/kit";
import { superValidate } from "sveltekit-superforms";
import { turmaSchema } from "./schema.js";
import { zod } from "sveltekit-superforms/adapters";
 
export const load: PageServerLoad = async () => {
    return {
        form: await superValidate(zod(turmaSchema)),
    };
};

export const actions: Actions = {
    default: async (event) => {
        let form = await superValidate(event, zod(turmaSchema));
        if (!form.valid) {
            return fail(400, { form, });
        }
        let id = form.data.turmaId;
        const url = "http://localhost:8080/api/turmas/"+id;
        const jsonData = JSON.stringify({ identificador: form.data.identificador});
        fetch(url, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        })
            .then((res) => {
                console.log(res); 
            })
            .catch((error) => {
                console.log(error);
            });
    }
}

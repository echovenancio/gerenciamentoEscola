import type { PageServerLoad } from "./$types.js";
import { fail, type Actions } from "@sveltejs/kit";
import { superValidate } from "sveltekit-superforms";
import { professorSchema } from "./schema.js";
import { zod } from "sveltekit-superforms/adapters";
 
export const load: PageServerLoad = async () => {
    return {
        form: await superValidate(zod(professorSchema)),
    };
};

export const actions: Actions = {
    default: async (event) => {
        let form = await superValidate(event, zod(professorSchema));
        if (!form.valid) {
            return fail(400, { form, });
        }
        let id = form.data.professorId;
        const url = "http://localhost:8080/api/professores/"+id;
        const jsonData = JSON.stringify({ nome: form.data.nome, email: form.data.email, idade: form.data.idade });
        fetch(url, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        })
            .then(async (res) => {
                console.log(res); 
            })
            .catch((error) => {
                console.log(error);
            });
        return { form };
    }
}

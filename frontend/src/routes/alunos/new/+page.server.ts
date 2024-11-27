import type { PageServerLoad, Actions } from "./$types.js";
import { fail } from "@sveltejs/kit";
import { superValidate } from "sveltekit-superforms";
import { alunoSchema } from "./schema.js";
import { zod } from "sveltekit-superforms/adapters";
 
export const load: PageServerLoad = async () => {
    return {
        form: await superValidate(zod(alunoSchema)),
    };
};

export const actions: Actions = {
    default: async (event) => {
        let form = await superValidate(event, zod(alunoSchema));
        if (!form.valid) {
            return fail(400, { form, });
        }
        const url = "http://localhost:8080/api/alunos";
        const jsonData = JSON.stringify(form.data);
        fetch(url, {
            method: "POST",
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

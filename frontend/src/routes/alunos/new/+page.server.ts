import type { PageServerLoad, Actions } from "./$types.js";
import { fail, redirect } from "@sveltejs/kit";
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
            .then(async (res) => {
                let resData = await res.json(); 
                console.log(res); 
                redirect(307, '/alunos/' + resData.id);
            })
            .catch((error) => {
                console.log(error);
            });
    }
}

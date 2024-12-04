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
        const url = "http://localhost:8080/api/professores";
        const jsonData = JSON.stringify(form.data);
        fetch(url, {
            method: "POST",
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

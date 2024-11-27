import type { PageServerLoad } from "./$types.js";
import { superValidate } from "sveltekit-superforms";
import { alunoSchema } from "./schema.js";
import { zod } from "sveltekit-superforms/adapters";
 
export const load: PageServerLoad = async () => {
    return {
        form: await superValidate(zod(alunoSchema)),
    };
};

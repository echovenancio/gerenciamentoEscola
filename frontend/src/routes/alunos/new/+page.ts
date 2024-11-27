import type { PageServerLoad } from "./$types.js";
import { superValidate } from "sveltekit-superforms";
import { alunoSchema } from "./schema.js";
import { zod } from "sveltekit-superforms/adapters";
import { writable } from "svelte/store";
 
export const load: PageServerLoad = async () => {
    return {
        form: await superValidate(zod(alunoSchema)),
    };
};

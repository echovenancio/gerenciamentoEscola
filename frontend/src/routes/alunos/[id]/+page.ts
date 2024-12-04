import type { PageServerLoad } from "./$types.js";
import { superValidate } from "sveltekit-superforms";
import { addAlunoTurma, alunoDisciplinaSchema, alunoEditSchema, notaSchema } from "./schema.js";
import { zod } from "sveltekit-superforms/adapters";
 
export const load: PageServerLoad = async ({params}) => {
    const disciplinaForm = await superValidate(zod(alunoDisciplinaSchema)); 
    const notaForm = await superValidate(zod(notaSchema));
    const alunoEditForm = await superValidate(zod(alunoEditSchema));
    const addAlunoTurmaForm = await superValidate(zod(addAlunoTurma));
    const id = params.id;
    return {
        disciplinaForm,
        notaForm,
        id,
        alunoEditForm,
        addAlunoTurmaForm
    };
};

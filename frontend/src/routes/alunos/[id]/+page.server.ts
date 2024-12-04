import type { PageServerLoad, Actions } from "./$types.js";
import { fail } from "@sveltejs/kit";
import { superValidate } from "sveltekit-superforms";
import { addAlunoTurma, alunoDisciplinaSchema, alunoEditSchema, notaSchema } from "./schema.js";
import { zod } from "sveltekit-superforms/adapters";
 
export const load: PageServerLoad = async ({params}) => {
    const disciplinaForm = await superValidate(zod(alunoDisciplinaSchema)); 
    const notaForm = await superValidate(zod(notaSchema));
    const alunoEditForm = await superValidate(zod(alunoEditSchema));
    const addAlunoTurmaForm = await superValidate(zod(addAlunoTurma));
    const id = params.id
    return {
        disciplinaForm,
        notaForm,
        alunoEditForm,
        addAlunoTurmaForm,
        id
    };
};

export const actions: Actions = {
    addDisciplina: async (event) => {
            console.log("rodando esse aqui")
            let id = event.params.id
            let form = await superValidate(event, zod(alunoDisciplinaSchema));
            console.log(form.data)
            if (!form.valid) {
                return fail(400, { form, });
            }
            const url = "http://localhost:8080/api/alunos/" + id + "/disciplinas";
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
    },
    setNota: async (event) => {
        let id = event.params.id;
        console.log(event.request)
        let form = await superValidate(event, zod(notaSchema));
        console.log(form.data)
        let disciplinaId = form.data.disciplinaId;
        const url = "http://localhost:8080/api/alunos/"+id+"/disciplinas/"+disciplinaId;
        const jsonData = JSON.stringify({ nota: form.data.nota, finalizar: form.data.finalizar});
        console.log(jsonData);
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
    },
    editAluno: async (event) => {
        let id = event.params.id;
        console.log(event.request)
        let form = await superValidate(event, zod(alunoEditSchema));
        console.log(form.data)
        const url = "http://localhost:8080/api/alunos/"+id;
        const jsonData = JSON.stringify(form.data);
        console.log(jsonData);
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
    },
    addToTurma: async (event) => {
        console.log(event.request)
        let form = await superValidate(event, zod(addAlunoTurma));
        console.log(form.data)
        const url = "http://localhost:8080/api/turmas/"+form.data.turmaId;
        const jsonData = JSON.stringify({addAlunosIds: [form.data.alunoId]});
        console.log(jsonData);
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

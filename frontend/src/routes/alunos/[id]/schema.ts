import { z } from "zod";

export const alunoDisciplinaSchema = z.object({
    disciplinaId: z.number(),
    professorId: z.number(),
});

export type AlunoDisciplinaSchema = z.infer<typeof alunoDisciplinaSchema>;

export const notaSchema = z.object({
    nota: z.number(),
    finalizar: z.boolean(),
    disciplinaId: z.number(),
});


export type NotaSchema = z.infer<typeof notaSchema>;

export const alunoEditSchema = z.object({
    nome: z.string(),
    email: z.string().email(),
    idade: z.number(),
})

export type AlunoEditSchema = z.infer<typeof alunoEditSchema>;

export const addAlunoTurma = z.object({
    alunoId: z.number(),
    turmaId: z.number()
})

export type AddAlunoTurmaSchema = z.infer<typeof addAlunoTurma>

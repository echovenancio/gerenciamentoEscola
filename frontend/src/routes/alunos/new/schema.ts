import { z } from "zod";

export const alunoSchema = z.object({
    matricula: z.string().min(5),
    nome: z.string().min(5),
    email: z.string().email(),
    idade: z.number(),
});

export type AlunoSchema = z.infer<typeof alunoSchema>;

import { z } from "zod";

export const professorSchema = z.object({
    professorId: z.number(),
    nome: z.string(),
    email: z.string().email(),
    idade: z.number(),
});

export type ProfessorSchema = z.infer<typeof professorSchema>;

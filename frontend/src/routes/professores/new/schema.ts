import { z } from "zod";

export const professorSchema = z.object({
    nome: z.string().min(5),
    email: z.string().email(),
    idade: z.number(),
});

export type ProfessorSchema = z.infer<typeof ProfessorSchema>;

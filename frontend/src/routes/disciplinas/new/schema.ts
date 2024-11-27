import { z } from "zod";

export const disciplinaSchema = z.object({
    nome: z.string().min(5),
});

export type DisciplinaSchema = z.infer<typeof disciplinaSchema>;

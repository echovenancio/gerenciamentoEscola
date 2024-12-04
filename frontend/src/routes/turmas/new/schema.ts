import { z } from "zod";

export const turmaSchema = z.object({
    identificador: z.string(),
});

export type TurmaSchema = z.infer<typeof turmaSchema>;

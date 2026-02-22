import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { createMessage, fetchMessages } from '../api/messages';

export const useMessages = (limit = 10) =>
  useQuery({
    queryKey: ['messages', limit],
    queryFn: () => fetchMessages(limit),
    staleTime: 60_000,
  });

export const useCreateMessage = (limit = 10) => {
  const qc = useQueryClient();

  return useMutation({
    mutationFn: (vars: { content: string; idempotencyKey: string }) =>
      createMessage(vars.content, vars.idempotencyKey),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['messages', limit] }),
  });
};

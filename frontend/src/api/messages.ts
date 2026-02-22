import { API_BASE, fetchJson } from './http.ts';

export interface Message {
  id: number;
  content: string;
  idempotencyKey: string;
  createdAt: string;
}

export const fetchMessages = (limit = 10): Promise<Message[]> =>
  fetchJson<Message[]>(`${API_BASE}/api/messages?limit=${limit}`);

export const createMessage = (content: string, idempotencyKey: string): Promise<Message> =>
  fetchJson<Message>(`${API_BASE}/api/messages`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Idempotency-Key': idempotencyKey,
    },
    body: JSON.stringify({ content }),
  });

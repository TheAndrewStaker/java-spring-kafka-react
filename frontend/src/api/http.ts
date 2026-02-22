export const API_BASE = import.meta.env.VITE_API_BASE ?? 'http://localhost:8080';

export const fetchJson = async <T>(input: RequestInfo, init?: RequestInit): Promise<T> => {
  const response = await fetch(input, init);

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || `Request failed (${response.status})`);
  }

  return (await response.json()) as Promise<T>;
};

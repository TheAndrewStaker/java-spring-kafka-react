import { useState } from 'react';
import './App.css';
import { useCreateMessage, useMessages } from './hooks/useMessages.ts';

function App() {
  const [messageValue, setMessageValue] = useState('');
  const { data: messages, isLoading, error } = useMessages(10);
  const create = useCreateMessage(10);

  const send = () => {
    const trimmedMessage = messageValue.trim();
    if (!trimmedMessage) return;

    create.mutate({ content: trimmedMessage, idempotencyKey: crypto.randomUUID() });
    setMessageValue('');
  };

  return (
    <div style={{ maxWidth: 720 }}>
      <h2>Backend Message Demo (TS + React Query)</h2>

      <div style={{ display: 'flex', gap: 8 }}>
        <input
          value={messageValue}
          onChange={(e) => setMessageValue(e.target.value)}
          placeholder='Type a message'
          style={{ padding: 10, flex: 1 }}
        />
        <button onClick={send} disabled={create.isPending} style={{ padding: '10px 14px' }}>
          Send
        </button>
      </div>

      {isLoading && <p>Loading...</p>}
      {error && <p>Failed to load messages</p>}

      <ul>
        {messages?.map((message) => (
          <li key={message.id}>
            <strong>{message.id}</strong> - {message.content}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;

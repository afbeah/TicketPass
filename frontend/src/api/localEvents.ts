import type { LocalEvent } from '../types/LocalEvent'

const API_URL = import.meta.env.VITE_API_URL

export async function getLocalEvents(): Promise<LocalEvent[]> {
    const response = await fetch(`${API_URL}/api/events/local`)

    if (!response.ok) {
        throw new Error('Não foi possível carregar os eventos do TicketPass')
    }

    return response.json()
}
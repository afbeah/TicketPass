import type { EventSearchResult } from '../types/EventSearchResult'

const API_URL = import.meta.env.VITE_API_URL

export async function searchEvents(
    keyword: string,
    city: string
): Promise<EventSearchResult[]> {
    const response = await fetch(
        `${API_URL}/api/events?keyword=${encodeURIComponent(keyword)}&city=${encodeURIComponent(city)}`
    )

    if (!response.ok) {
        throw new Error('Não foi possível buscar os eventos')
    }

    return response.json()
}
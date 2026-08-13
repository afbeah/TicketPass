const API_URL = import.meta.env.VITE_API_URL

export interface MyTicket {
    ticketId: string
    eventName: string
    location: string
    startDateTime: string
    ticketType: string
    price: number
    status: string
    qrCode: string
}

export async function getMyTickets(): Promise<MyTicket[]> {
    const token = localStorage.getItem('ticketpass_token')

    if (!token) {
        throw new Error('Usuário não autenticado')
    }

    const response = await fetch(`${API_URL}/api/tickets/my`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })

    if (!response.ok) {
        throw new Error('Não foi possível carregar os ingressos')
    }

    return response.json()
}
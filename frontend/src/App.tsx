import { useEffect, useState } from 'react'
import './App.css'
import Login from './pages/Login'
import { searchEvents } from './api/events'
import { getLocalEvents } from './api/localEvents'
import { createReservation } from './api/reservations'
import {
  createPayment,
  approvePayment,
} from './api/payments'
import type { PaymentMethod } from './api/payments'
import type { EventSearchResult } from './types/EventSearchResult'
import type { LocalEvent } from './types/LocalEvent'

function App() {
  const [keyword, setKeyword] = useState('')
  const [city, setCity] = useState('')

  const [events, setEvents] = useState<EventSearchResult[]>([])
  const [localEvents, setLocalEvents] = useState<LocalEvent[]>([])

  const [loading, setLoading] = useState(false)
  const [loadingLocalEvents, setLoadingLocalEvents] = useState(true)

  const [error, setError] = useState('')
  const [localEventsError, setLocalEventsError] = useState('')

  const [showLogin, setShowLogin] = useState(false)

  const [token, setToken] = useState(
      localStorage.getItem('ticketpass_token')
  )

  const [reserving, setReserving] = useState(false)
  const [reservationMessage, setReservationMessage] = useState('')

  const [reservationId, setReservationId] = useState<string | null>(null)
  const [showPayment, setShowPayment] = useState(false)

  const [paymentLoading, setPaymentLoading] = useState(false)
  const [paymentMessage, setPaymentMessage] = useState('')

  useEffect(() => {
    async function loadLocalEvents() {
      try {
        setLoadingLocalEvents(true)
        setLocalEventsError('')

        const results = await getLocalEvents()

        setLocalEvents(results)
      } catch {
        setLocalEvents([])
        setLocalEventsError(
            'Não foi possível carregar os eventos do TicketPass.'
        )
      } finally {
        setLoadingLocalEvents(false)
      }
    }

    loadLocalEvents()
  }, [])

  async function handleSearch() {
    if (!keyword.trim() || !city.trim()) {
      setError('Informe o evento e a cidade para realizar a busca.')
      return
    }

    try {
      setLoading(true)
      setError('')

      const results = await searchEvents(keyword, city)

      setEvents(results)
    } catch {
      setEvents([])
      setError('Não foi possível buscar os eventos.')
    } finally {
      setLoading(false)
    }
  }

  async function handlePurchase(ticketId: string) {
    if (!token) {
      setShowLogin(true)
      return
    }

    try {
      setReserving(true)
      setReservationMessage('')
      setPaymentMessage('')

      const reservation = await createReservation(ticketId)

      setReservationId(reservation.id)

      setReservationMessage(
          'Ingresso reservado! Escolha uma forma de pagamento.'
      )

      setShowPayment(true)
    } catch {
      setReservationMessage(
          'Não foi possível reservar o ingresso.'
      )
    } finally {
      setReserving(false)
    }
  }

  async function handlePayment(method: PaymentMethod) {
    if (!reservationId) {
      setPaymentMessage(
          'Nenhuma reserva disponível para pagamento.'
      )
      return
    }

    try {
      setPaymentLoading(true)
      setPaymentMessage('')

      const payment = await createPayment(
          reservationId,
          method
      )

      await approvePayment(payment.id)

      setPaymentMessage(
          'Pagamento aprovado! Seu ingresso foi confirmado.'
      )

      setShowPayment(false)
    } catch {
      setPaymentMessage(
          'Não foi possível processar o pagamento.'
      )
    } finally {
      setPaymentLoading(false)
    }
  }

  if (showLogin && !token) {
    return (
        <Login
            onLoginSuccess={(newToken) => {
              setToken(newToken)
              setShowLogin(false)
            }}
        />
    )
  }

  return (
      <main className="app">
        <header className="header">
          <h1>TicketPass</h1>

          <nav>
            <button>Eventos</button>
            <button>Meus ingressos</button>

            {!token ? (
                <button onClick={() => setShowLogin(true)}>
                  Entrar
                </button>
            ) : (
                <button
                    onClick={() => {
                      localStorage.removeItem('ticketpass_token')
                      setToken(null)
                      setReservationId(null)
                      setShowPayment(false)
                    }}
                >
                  Sair
                </button>
            )}
          </nav>
        </header>

        <section className="hero">
          <div>
            <span className="eyebrow">VIVA O EVENTO</span>

            <h2>
              Encontre seu próximo
              <br />
              evento.
            </h2>

            <p>
              Descubra eventos, reserve seus ingressos e viva experiências
              inesquecíveis.
            </p>
          </div>
        </section>

        <section className="events">
          <div className="section-header">
            <span className="eyebrow">EVENTOS</span>

            <h3>Encontre o que está acontecendo</h3>
          </div>

          <div className="search">
            <input
                type="text"
                placeholder="O que você está procurando?"
                value={keyword}
                onChange={(event) => setKeyword(event.target.value)}
            />

            <input
                type="text"
                placeholder="Cidade"
                value={city}
                onChange={(event) => setCity(event.target.value)}
            />

            <button onClick={handleSearch}>
              {loading ? 'Buscando...' : 'Buscar eventos'}
            </button>
          </div>

          {error && <p className="error">{error}</p>}

          {events.length > 0 && (
              <>
                <div className="section-header event-results-header">
                  <span className="eyebrow">DESCOBERTA</span>

                  <h3>Eventos encontrados</h3>
                </div>

                <div className="event-grid">
                  {events.map((event) => (
                      <article
                          className="event-card"
                          key={event.id}
                      >
                        <img
                            src={event.imageUrl}
                            alt={event.name}
                        />

                        <div className="event-card-content">
                    <span className="event-date">
                      {event.date} • {event.time}
                    </span>

                          <h4>{event.name}</h4>

                          <p>
                            {event.venue} · {event.city}, {event.state}
                          </p>

                          <a
                              href={event.url}
                              target="_blank"
                              rel="noreferrer"
                          >
                            Ver evento
                          </a>
                        </div>
                      </article>
                  ))}
                </div>
              </>
          )}

          <div className="local-events-section">
            <div className="section-header">
              <span className="eyebrow">TICKETPASS</span>

              <h3>Eventos disponíveis para compra</h3>
            </div>

            {loadingLocalEvents && (
                <p className="loading-message">
                  Carregando eventos...
                </p>
            )}

            {localEventsError && (
                <p className="error">
                  {localEventsError}
                </p>
            )}

            {!loadingLocalEvents &&
                !localEventsError &&
                localEvents.length === 0 && (
                    <p className="loading-message">
                      Nenhum evento disponível no momento.
                    </p>
                )}

            {localEvents.length > 0 && (
                <div className="event-grid">
                  {localEvents.map((event) => (
                      <article
                          className="event-card local-event-card"
                          key={event.ticketId}
                      >
                        <div className="local-event-placeholder">
                    <span className="eyebrow">
                      TICKETPASS
                    </span>

                          <strong>🎫</strong>
                        </div>

                        <div className="event-card-content">
                    <span className="event-date">
                      {new Date(
                          event.startDateTime
                      ).toLocaleDateString('pt-BR')}
                    </span>

                          <h4>{event.name}</h4>

                          <p>{event.location}</p>

                          <p>{event.description}</p>

                          <div className="local-event-footer">
                            <strong>
                              R$ {event.ticketPrice.toFixed(2)}
                            </strong>

                            <button
                                onClick={() =>
                                    handlePurchase(event.ticketId)
                                }
                                disabled={reserving}
                            >
                              {reserving
                                  ? 'Reservando...'
                                  : 'Comprar'}
                            </button>
                          </div>
                        </div>
                      </article>
                  ))}
                </div>
            )}

            {reservationMessage && (
                <p className="success">
                  {reservationMessage}
                </p>
            )}

            {showPayment && reservationId && (
                <section className="payment-section">
                  <div className="section-header">
                <span className="eyebrow">
                  PAGAMENTO
                </span>

                    <h3>Finalize sua compra</h3>
                  </div>

                  <p>
                    Pagamento simulado. Nenhum valor real será cobrado.
                  </p>

                  <div className="payment-options">
                    <button
                        onClick={() =>
                            handlePayment('PIX')
                        }
                        disabled={paymentLoading}
                    >
                      {paymentLoading
                          ? 'Processando...'
                          : 'Pagar com PIX'}
                    </button>

                    <button
                        onClick={() =>
                            handlePayment('CREDIT_CARD')
                        }
                        disabled={paymentLoading}
                    >
                      {paymentLoading
                          ? 'Processando...'
                          : 'Pagar com cartão'}
                    </button>
                  </div>
                </section>
            )}

            {paymentMessage && (
                <p className="success">
                  {paymentMessage}
                </p>
            )}
          </div>
        </section>
      </main>
  )
}

export default App
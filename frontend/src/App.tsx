import { useState } from 'react'
import './App.css'
import Login from './pages/Login'
import { searchEvents } from './api/events'
import type { EventSearchResult } from './types/EventSearchResult'

function App() {
  const [keyword, setKeyword] = useState('')
  const [city, setCity] = useState('')
  const [events, setEvents] = useState<EventSearchResult[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [showLogin, setShowLogin] = useState(false)
  const [token, setToken] = useState(
      localStorage.getItem('ticketpass_token')
  )

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
              <div className="event-grid">
                {events.map((event) => (
                    <article className="event-card" key={event.id}>
                      <img src={event.imageUrl} alt={event.name} />

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
          )}
        </section>
      </main>
  )
}

export default App
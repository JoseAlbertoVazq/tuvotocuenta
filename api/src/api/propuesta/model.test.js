import { Propuesta } from '.'
import { User } from '../user'

let user, propuesta

beforeEach(async () => {
  user = await User.create({ email: 'a@a.com', password: '123456' })
  propuesta = await Propuesta.create({ creador: user, titulo: 'test', contenido: 'test', materia: 'test', partido: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = propuesta.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(propuesta.id)
    expect(typeof view.creador).toBe('object')
    expect(view.creador.id).toBe(user.id)
    expect(view.titulo).toBe(propuesta.titulo)
    expect(view.contenido).toBe(propuesta.contenido)
    expect(view.materia).toBe(propuesta.materia)
    expect(view.partido).toBe(propuesta.partido)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = propuesta.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(propuesta.id)
    expect(typeof view.creador).toBe('object')
    expect(view.creador.id).toBe(user.id)
    expect(view.titulo).toBe(propuesta.titulo)
    expect(view.contenido).toBe(propuesta.contenido)
    expect(view.materia).toBe(propuesta.materia)
    expect(view.partido).toBe(propuesta.partido)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})

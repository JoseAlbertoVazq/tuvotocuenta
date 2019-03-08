import { Partido } from '.'

let partido

beforeEach(async () => {
  partido = await Partido.create({ nombre: 'test', siglas: 'test', propuestas: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = partido.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(partido.id)
    expect(view.nombre).toBe(partido.nombre)
    expect(view.siglas).toBe(partido.siglas)
    expect(view.propuestas).toBe(partido.propuestas)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = partido.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(partido.id)
    expect(view.nombre).toBe(partido.nombre)
    expect(view.siglas).toBe(partido.siglas)
    expect(view.propuestas).toBe(partido.propuestas)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})

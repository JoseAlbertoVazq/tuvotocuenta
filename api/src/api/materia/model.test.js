import { Materia } from '.'

let materia

beforeEach(async () => {
  materia = await Materia.create({ nombre: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = materia.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(materia.id)
    expect(view.nombre).toBe(materia.nombre)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = materia.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(materia.id)
    expect(view.nombre).toBe(materia.nombre)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})

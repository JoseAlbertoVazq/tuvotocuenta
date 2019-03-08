import { success, notFound } from '../../services/response/'
import { Materia } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Materia.create(body)
    .then((materia) => materia.view())
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Materia.count(query)
    .then(count => Materia.find(query, select, cursor)
      .then((materias) => ({
        count,
        rows: materias.map((materia) => materia.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Materia.findById(params.id)
    .then(notFound(res))
    .then((materia) => materia ? materia.view(true) : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Materia.findById(params.id)
    .then(notFound(res))
    .then((materia) => materia ? Object.assign(materia, body).save() : null)
    .then((materia) => materia ? materia.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Materia.findById(params.id)
    .then(notFound(res))
    .then((materia) => materia ? materia.remove() : null)
    .then(success(res, 204))
    .catch(next)

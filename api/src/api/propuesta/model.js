import mongoose, { Schema } from 'mongoose'

const propuestaSchema = new Schema({
  creador: {
    type: Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  titulo: {
    type: String
  },
  contenido: {
    type: String
  },
  materia: {
    type: Schema.Types.ObjectId,
    ref: 'Materia'
  },
  partido: {
    type: Schema.Types.ObjectId,
    ref: 'Partido'
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

propuestaSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      creador: this.creador.view(full),
      titulo: this.titulo,
      contenido: this.contenido,
      materia: this.materia,
      partido: this.partido,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Propuesta', propuestaSchema)

export const schema = model.schema
export default model

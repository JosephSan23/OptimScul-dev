import * as XLSX from 'xlsx';
import { MatrizAsistencia } from '../services/reporte.service';

const LABEL: Record<string, string> = {
  PRESENTE: 'Presente',
  AUSENTE: 'Ausente',
  TARDE: 'Tarde',
  JUSTIFICADA: 'Justificada',
  '': '—',
};

export function descargarMatrizExcel(
  m: MatrizAsistencia,
  nombreArchivo: string,
): void {
  const header = [
    'Código',
    'Documento',
    'Estudiante',
    ...m.sesiones.map((s) => s.fecha),
  ];
  const filas = m.estudiantes.map((e) => [
    e.codigoEstudiante,
    e.numeroDocumento,
    e.nombre,
    ...e.marcas.map((x) => LABEL[x] ?? x),
  ]);
  const hoja = XLSX.utils.aoa_to_sheet([header, ...filas]);
  const libro = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(libro, hoja, 'Asistencia');
  XLSX.writeFile(libro, nombreArchivo);
}

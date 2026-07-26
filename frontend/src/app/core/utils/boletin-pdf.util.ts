import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { MisNotasVista } from '../services/rol/estudiante.service';


export interface BoletinBranding {
  colorPrimario: [number, number, number];
  colorAcento: [number, number, number];
  lema?: string;
  logoDataUrl?: string;
}

export const BRANDING_DEFECTO: BoletinBranding = {
  colorPrimario: [17, 24, 39], // casi negro
  colorAcento: [161, 98, 7], // dorado/mostaza
  lema: 'FRASE MOTIVADORA',
};

export interface BoletinContexto {
  anioNombre: string;
  periodoNombre: string;
}

export function generarBoletinPdf(
  vista: MisNotasVista,
  ctx: BoletinContexto,
  branding: BoletinBranding = BRANDING_DEFECTO,
): void {
  const doc = new jsPDF('p', 'mm', 'a4');
  const W = doc.internal.pageSize.getWidth();
  const H = doc.internal.pageSize.getHeight();
  const [pr, pg, pb] = branding.colorPrimario;
  const [ar, ag, ab] = branding.colorAcento;
  const MARGIN = 10;

  // ── Marco decorativo doble ──
  doc.setDrawColor(ar, ag, ab);
  doc.setLineWidth(1);
  doc.rect(MARGIN, MARGIN, W - MARGIN * 2, H - MARGIN * 2);
  doc.setLineWidth(0.3);
  doc.rect(MARGIN + 2, MARGIN + 2, W - (MARGIN + 2) * 2, H - (MARGIN + 2) * 2);

  let y = MARGIN + 14;

  // ── Escudo / logo centrado ──
  if (branding.logoDataUrl) {
    try {
      doc.addImage(branding.logoDataUrl, 'PNG', W / 2 - 10, y, 20, 20);
      y += 26;
    } catch {}
  }

  // ── Nombre institución centrado, mayúsculas ──
  doc.setTextColor(pr, pg, pb);
  doc.setFont('times', 'bold');
  doc.setFontSize(17);
  doc.text((vista.institucionNombre || 'INSTITUCIÓN').toUpperCase(), W / 2, y, {
    align: 'center',
  });
  y += 6;
  if (branding.lema) {
    doc.setFont('times', 'italic');
    doc.setFontSize(10);
    doc.setTextColor(90, 90, 90);
    doc.text(branding.lema, W / 2, y, { align: 'center' });
    y += 6;
  }

  // Línea decorativa con rombo central
  doc.setDrawColor(ar, ag, ab);
  doc.setLineWidth(0.5);
  doc.line(W / 2 - 40, y, W / 2 - 4, y);
  doc.line(W / 2 + 4, y, W / 2 + 40, y);
  doc.setFillColor(ar, ag, ab);
  doc.circle(W / 2, y, 1.2, 'F');
  y += 10;

  // ── Título del certificado ──
  doc.setFont('times', 'bold');
  doc.setFontSize(13);
  doc.setTextColor(pr, pg, pb);
  doc.text('BOLETÍN DE CALIFICACIONES', W / 2, y, { align: 'center' });
  y += 6;
  doc.setFont('times', 'normal');
  doc.setFontSize(10);
  doc.setTextColor(110, 110, 110);
  doc.text(`${ctx.anioNombre} — ${ctx.periodoNombre}`, W / 2, y, { align: 'center' });
  y += 12;

  // ── Datos del estudiante en formato "certificado" ──
  doc.setFont('times', 'normal');
  doc.setFontSize(10.5);
  doc.setTextColor(60, 60, 60);
  doc.text('Se certifica que el/la estudiante', W / 2, y, { align: 'center' });
  y += 8;
  doc.setFont('times', 'bold');
  doc.setFontSize(15);
  doc.setTextColor(pr, pg, pb);
  doc.text((vista.estudianteNombre || 'Estudiante').toUpperCase(), W / 2, y, {
    align: 'center',
  });
  y += 7;
  doc.setFont('times', 'italic');
  doc.setFontSize(10);
  doc.setTextColor(90, 90, 90);
  doc.text(`${vista.gradoNombre ?? ''} · ${vista.grupoNombre ?? ''}`, W / 2, y, {
    align: 'center',
  });
  y += 6;
  doc.setFont('times', 'normal');
  doc.setFontSize(9.5);
  doc.text('obtuvo las siguientes calificaciones durante el periodo:', W / 2, y, {
    align: 'center',
  });
  y += 8;

  // ── Tabla de materias, estilo formal (líneas horizontales solamente) ──
  autoTable(doc, {
    startY: y,
    margin: { left: MARGIN + 10, right: MARGIN + 10 },
    head: [['Asignatura', 'Docente', 'Nota', 'Estado']],
    body: vista.materias.map((m) => [
      m.asignaturaNombre,
      m.profesorNombre,
      m.notaFinal != null ? String(m.notaFinal) : '—',
      m.notaFinal == null ? 'Sin notas' : m.aprueba ? 'Aprueba' : 'Reprueba',
    ]),
    theme: 'plain',
    styles: {
      font: 'times',
      fontSize: 10,
      cellPadding: { top: 3.5, bottom: 3.5, left: 2, right: 2 },
      lineWidth: { top: 0.2, bottom: 0.2 },
      lineColor: [ar, ag, ab],
    },
    headStyles: {
      fontStyle: 'bold',
      textColor: [pr, pg, pb],
      lineWidth: { top: 0.5, bottom: 0.5 },
      lineColor: [ar, ag, ab],
    },
    columnStyles: {
      2: { halign: 'center', fontStyle: 'bold' },
      3: { halign: 'center' },
    },
    didParseCell: (d) => {
      if (d.section === 'body' && d.column.index === 3) {
        const t = d.cell.text[0];
        if (t === 'Reprueba') d.cell.styles.textColor = [178, 34, 34];
        else if (t === 'Aprueba') d.cell.styles.textColor = [30, 90, 60];
      }
    },
  });

  // ── Promedio destacado ──
  const fy = (doc as any).lastAutoTable.finalY + 10;
  if (vista.promedio != null) {
    doc.setFont('times', 'bold');
    doc.setFontSize(11);
    doc.setTextColor(pr, pg, pb);
    doc.text(`Promedio general del periodo: ${vista.promedio}`, W / 2, fy, {
      align: 'center',
    });
  }

  // ── Doble firma ──
  const firmaY = H - MARGIN - 22;
  const firmaAncho = 55;
  const x1 = W / 2 - firmaAncho - 10;
  const x2 = W / 2 + 10;

  doc.setDrawColor(120);
  doc.setLineWidth(0.3);
  doc.line(x1, firmaY, x1 + firmaAncho, firmaY);
  doc.line(x2, firmaY, x2 + firmaAncho, firmaY);

  doc.setFont('times', 'normal');
  doc.setFontSize(8.5);
  doc.setTextColor(110, 110, 110);
  doc.text('Director(a) de grupo', x1 + firmaAncho / 2, firmaY + 5, { align: 'center' });
  doc.text('Coordinación académica', x2 + firmaAncho / 2, firmaY + 5, { align: 'center' });

  // ── Fecha de generación en la esquina ──
  doc.setFontSize(7.5);
  doc.setTextColor(150, 150, 150);
  doc.text(
    'Generado: ' + new Date().toLocaleDateString('es-CO'),
    W - MARGIN - 4,
    H - MARGIN - 4,
    { align: 'right' },
  );

  doc.save(
    `boletin_${(vista.estudianteNombre || 'estudiante').replace(/\s+/g, '_')}_${ctx.periodoNombre.replace(/\s+/g, '_')}.pdf`,
  );
}
